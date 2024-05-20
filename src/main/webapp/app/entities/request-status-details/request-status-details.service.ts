import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';

type EntityResponseType = HttpResponse<IRequestStatusDetails>;
type EntityArrayResponseType = HttpResponse<IRequestStatusDetails[]>;

@Injectable({ providedIn: 'root' })
export class RequestStatusDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/request-status-details';

  constructor(protected http: HttpClient) {}

  create(requestStatusDetails: IRequestStatusDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestStatusDetails);
    return this.http
      .post<IRequestStatusDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requestStatusDetails: IRequestStatusDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestStatusDetails);
    return this.http
      .put<IRequestStatusDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequestStatusDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequestStatusDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(requestStatusDetails: IRequestStatusDetails): IRequestStatusDetails {
    const copy: IRequestStatusDetails = Object.assign({}, requestStatusDetails, {
      decisionDate:
        requestStatusDetails.decisionDate != null && requestStatusDetails.decisionDate.isValid()
          ? requestStatusDetails.decisionDate.toJSON()
          : null,
      createdDate:
        requestStatusDetails.createdDate != null && requestStatusDetails.createdDate.isValid()
          ? requestStatusDetails.createdDate.toJSON()
          : null,
      modifiedDate:
        requestStatusDetails.modifiedDate != null && requestStatusDetails.modifiedDate.isValid()
          ? requestStatusDetails.modifiedDate.toJSON()
          : null,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.decisionDate = res.body.decisionDate != null ? moment(res.body.decisionDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((requestStatusDetails: IRequestStatusDetails) => {
        requestStatusDetails.decisionDate = requestStatusDetails.decisionDate != null ? moment(requestStatusDetails.decisionDate) : null;
        requestStatusDetails.createdDate = requestStatusDetails.createdDate != null ? moment(requestStatusDetails.createdDate) : null;
        requestStatusDetails.modifiedDate = requestStatusDetails.modifiedDate != null ? moment(requestStatusDetails.modifiedDate) : null;
      });
    }
    return res;
  }
}
