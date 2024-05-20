import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmailTemplate } from 'app/shared/model/email-template.model';

type EntityResponseType = HttpResponse<IEmailTemplate>;
type EntityArrayResponseType = HttpResponse<IEmailTemplate[]>;

@Injectable({ providedIn: 'root' })
export class EmailTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/email-templates';

  constructor(protected http: HttpClient) {}

  create(emailTemplate: IEmailTemplate): Observable<EntityResponseType> {
    return this.http.post<IEmailTemplate>(this.resourceUrl, emailTemplate, { observe: 'response' });
  }

  update(emailTemplate: IEmailTemplate): Observable<EntityResponseType> {
    return this.http.put<IEmailTemplate>(this.resourceUrl, emailTemplate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
