import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITechnicalDetails } from 'app/shared/model/technical-details.model';

type EntityResponseType = HttpResponse<ITechnicalDetails>;
type EntityArrayResponseType = HttpResponse<ITechnicalDetails[]>;

@Injectable({ providedIn: 'root' })
export class TechnicalDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/technical-details';

  constructor(protected http: HttpClient) {}

  create(technicalDetails: ITechnicalDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(technicalDetails);
    return this.http
      .post<ITechnicalDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(technicalDetails: ITechnicalDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(technicalDetails);
    return this.http
      .put<ITechnicalDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITechnicalDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITechnicalDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(technicalDetails: ITechnicalDetails): ITechnicalDetails {
    const copy: ITechnicalDetails = Object.assign({}, technicalDetails, {
      targetIngestionStartDate:
        technicalDetails.targetIngestionStartDate != null && technicalDetails.targetIngestionStartDate.isValid()
          ? technicalDetails.targetIngestionStartDate.format(DATE_FORMAT)
          : null,
      targetIngestionEndDate:
        technicalDetails.targetIngestionEndDate != null && technicalDetails.targetIngestionEndDate.isValid()
          ? technicalDetails.targetIngestionEndDate.format(DATE_FORMAT)
          : null,
      createdDate:
        technicalDetails.createdDate != null && technicalDetails.createdDate.isValid() ? technicalDetails.createdDate.toJSON() : null,
      modifiedDate:
        technicalDetails.modifiedDate != null && technicalDetails.modifiedDate.isValid() ? technicalDetails.modifiedDate.toJSON() : null,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.targetIngestionStartDate = res.body.targetIngestionStartDate != null ? moment(res.body.targetIngestionStartDate) : null;
      res.body.targetIngestionEndDate = res.body.targetIngestionEndDate != null ? moment(res.body.targetIngestionEndDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((technicalDetails: ITechnicalDetails) => {
        technicalDetails.targetIngestionStartDate =
          technicalDetails.targetIngestionStartDate != null ? moment(technicalDetails.targetIngestionStartDate) : null;
        technicalDetails.targetIngestionEndDate =
          technicalDetails.targetIngestionEndDate != null ? moment(technicalDetails.targetIngestionEndDate) : null;
        technicalDetails.createdDate = technicalDetails.createdDate != null ? moment(technicalDetails.createdDate) : null;
        technicalDetails.modifiedDate = technicalDetails.modifiedDate != null ? moment(technicalDetails.modifiedDate) : null;
      });
    }
    return res;
  }
}
