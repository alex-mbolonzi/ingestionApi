import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

type EntityResponseType = HttpResponse<IDatasetUserUsageRestriction>;
type EntityArrayResponseType = HttpResponse<IDatasetUserUsageRestriction[]>;

@Injectable({ providedIn: 'root' })
export class DatasetUserUsageRestrictionService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-user-usage-restrictions';

  constructor(protected http: HttpClient) {}

  create(datasetUserUsageRestriction: IDatasetUserUsageRestriction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datasetUserUsageRestriction);
    return this.http
      .post<IDatasetUserUsageRestriction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(datasetUserUsageRestriction: IDatasetUserUsageRestriction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datasetUserUsageRestriction);
    return this.http
      .put<IDatasetUserUsageRestriction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDatasetUserUsageRestriction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDatasetUserUsageRestriction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(datasetUserUsageRestriction: IDatasetUserUsageRestriction): IDatasetUserUsageRestriction {
    const copy: IDatasetUserUsageRestriction = Object.assign({}, datasetUserUsageRestriction, {
      createdDate:
        datasetUserUsageRestriction.createdDate != null && datasetUserUsageRestriction.createdDate.isValid()
          ? datasetUserUsageRestriction.createdDate.toJSON()
          : null,
      modifiedDate:
        datasetUserUsageRestriction.modifiedDate != null && datasetUserUsageRestriction.modifiedDate.isValid()
          ? datasetUserUsageRestriction.modifiedDate.toJSON()
          : null,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((datasetUserUsageRestriction: IDatasetUserUsageRestriction) => {
        datasetUserUsageRestriction.createdDate =
          datasetUserUsageRestriction.createdDate != null ? moment(datasetUserUsageRestriction.createdDate) : null;
        datasetUserUsageRestriction.modifiedDate =
          datasetUserUsageRestriction.modifiedDate != null ? moment(datasetUserUsageRestriction.modifiedDate) : null;
      });
    }
    return res;
  }
}
