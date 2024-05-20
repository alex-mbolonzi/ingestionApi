import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IValidationNotes } from 'app/shared/model/validation-notes.model';

type EntityResponseType = HttpResponse<IValidationNotes>;
type EntityArrayResponseType = HttpResponse<IValidationNotes[]>;

@Injectable({ providedIn: 'root' })
export class ValidationNotesService {
  public resourceUrl = SERVER_API_URL + 'api/validation-notes';

  constructor(protected http: HttpClient) {}

  create(validationNotes: IValidationNotes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(validationNotes);
    return this.http
      .post<IValidationNotes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(validationNotes: IValidationNotes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(validationNotes);
    return this.http
      .put<IValidationNotes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IValidationNotes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IValidationNotes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(validationNotes: IValidationNotes): IValidationNotes {
    const copy: IValidationNotes = Object.assign({}, validationNotes, {
      createdDate:
        validationNotes.createdDate != null && validationNotes.createdDate.isValid() ? validationNotes.createdDate.toJSON() : null,
      modifiedDate:
        validationNotes.modifiedDate != null && validationNotes.modifiedDate.isValid() ? validationNotes.modifiedDate.toJSON() : null,
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
      res.body.forEach((validationNotes: IValidationNotes) => {
        validationNotes.createdDate = validationNotes.createdDate != null ? moment(validationNotes.createdDate) : null;
        validationNotes.modifiedDate = validationNotes.modifiedDate != null ? moment(validationNotes.modifiedDate) : null;
      });
    }
    return res;
  }
}
