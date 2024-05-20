import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';

type EntityResponseType = HttpResponse<IDatasetIndication>;
type EntityArrayResponseType = HttpResponse<IDatasetIndication[]>;

@Injectable({ providedIn: 'root' })
export class DatasetIndicationService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-indications';

  constructor(protected http: HttpClient) {}

  create(datasetIndication: IDatasetIndication): Observable<EntityResponseType> {
    return this.http.post<IDatasetIndication>(this.resourceUrl, datasetIndication, { observe: 'response' });
  }

  update(datasetIndication: IDatasetIndication): Observable<EntityResponseType> {
    return this.http.put<IDatasetIndication>(this.resourceUrl, datasetIndication, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetIndication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetIndication[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
