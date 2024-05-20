import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';

type EntityResponseType = HttpResponse<IDatasetTherapy>;
type EntityArrayResponseType = HttpResponse<IDatasetTherapy[]>;

@Injectable({ providedIn: 'root' })
export class DatasetTherapyService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-therapies';

  constructor(protected http: HttpClient) {}

  create(datasetTherapy: IDatasetTherapy): Observable<EntityResponseType> {
    return this.http.post<IDatasetTherapy>(this.resourceUrl, datasetTherapy, { observe: 'response' });
  }

  update(datasetTherapy: IDatasetTherapy): Observable<EntityResponseType> {
    return this.http.put<IDatasetTherapy>(this.resourceUrl, datasetTherapy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetTherapy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetTherapy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
