import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

type EntityResponseType = HttpResponse<IDatasetRoleDetails>;
type EntityArrayResponseType = HttpResponse<IDatasetRoleDetails[]>;

@Injectable({ providedIn: 'root' })
export class DatasetRoleDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-role-details';

  constructor(protected http: HttpClient) {}

  create(datasetRoleDetails: IDatasetRoleDetails): Observable<EntityResponseType> {
    return this.http.post<IDatasetRoleDetails>(this.resourceUrl, datasetRoleDetails, { observe: 'response' });
  }

  update(datasetRoleDetails: IDatasetRoleDetails): Observable<EntityResponseType> {
    return this.http.put<IDatasetRoleDetails>(this.resourceUrl, datasetRoleDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetRoleDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetRoleDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
