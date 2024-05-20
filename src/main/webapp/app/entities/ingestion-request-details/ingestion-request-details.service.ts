import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

type EntityResponseType = HttpResponse<IIngestionRequestDetails>;
type EntityArrayResponseType = HttpResponse<IIngestionRequestDetails[]>;

@Injectable({ providedIn: 'root' })
export class IngestionRequestDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/ingestion-request-details';

  constructor(protected http: HttpClient) {}

  create(ingestionRequestDetails: IIngestionRequestDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ingestionRequestDetails);
    return this.http
      .post<IIngestionRequestDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ingestionRequestDetails: IIngestionRequestDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ingestionRequestDetails);
    return this.http
      .put<IIngestionRequestDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIngestionRequestDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIngestionRequestDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ingestionRequestDetails: IIngestionRequestDetails): IIngestionRequestDetails {
    const copy: IIngestionRequestDetails = Object.assign({}, ingestionRequestDetails, {
      createdDate:
        ingestionRequestDetails.createdDate != null && ingestionRequestDetails.createdDate.isValid()
          ? ingestionRequestDetails.createdDate.toJSON()
          : null,
      modifiedDate:
        ingestionRequestDetails.modifiedDate != null && ingestionRequestDetails.modifiedDate.isValid()
          ? ingestionRequestDetails.modifiedDate.toJSON()
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
      res.body.forEach((ingestionRequestDetails: IIngestionRequestDetails) => {
        ingestionRequestDetails.createdDate =
          ingestionRequestDetails.createdDate != null ? moment(ingestionRequestDetails.createdDate) : null;
        ingestionRequestDetails.modifiedDate =
          ingestionRequestDetails.modifiedDate != null ? moment(ingestionRequestDetails.modifiedDate) : null;
      });
    }
    return res;
  }
}
