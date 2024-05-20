import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

type EntityResponseType = HttpResponse<IApplicationReferenceTable>;
type EntityArrayResponseType = HttpResponse<IApplicationReferenceTable[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationReferenceTableService {
  public resourceUrl = SERVER_API_URL + 'api/application-reference-tables';

  constructor(protected http: HttpClient) {}

  create(applicationReferenceTable: IApplicationReferenceTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationReferenceTable);
    return this.http
      .post<IApplicationReferenceTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationReferenceTable: IApplicationReferenceTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationReferenceTable);
    return this.http
      .put<IApplicationReferenceTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationReferenceTable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationReferenceTable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationReferenceTable: IApplicationReferenceTable): IApplicationReferenceTable {
    const copy: IApplicationReferenceTable = Object.assign({}, applicationReferenceTable, {
      createdDate:
        applicationReferenceTable.createdDate != null && applicationReferenceTable.createdDate.isValid()
          ? applicationReferenceTable.createdDate.toJSON()
          : null,
      modifiedDate:
        applicationReferenceTable.modifiedDate != null && applicationReferenceTable.modifiedDate.isValid()
          ? applicationReferenceTable.modifiedDate.toJSON()
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
      res.body.forEach((applicationReferenceTable: IApplicationReferenceTable) => {
        applicationReferenceTable.createdDate =
          applicationReferenceTable.createdDate != null ? moment(applicationReferenceTable.createdDate) : null;
        applicationReferenceTable.modifiedDate =
          applicationReferenceTable.modifiedDate != null ? moment(applicationReferenceTable.modifiedDate) : null;
      });
    }
    return res;
  }
}
