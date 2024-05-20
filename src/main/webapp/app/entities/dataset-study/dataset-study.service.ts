import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetStudy } from 'app/shared/model/dataset-study.model';

type EntityResponseType = HttpResponse<IDatasetStudy>;
type EntityArrayResponseType = HttpResponse<IDatasetStudy[]>;

@Injectable({ providedIn: 'root' })
export class DatasetStudyService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-studies';

  constructor(protected http: HttpClient) {}

  create(datasetStudy: IDatasetStudy): Observable<EntityResponseType> {
    return this.http.post<IDatasetStudy>(this.resourceUrl, datasetStudy, { observe: 'response' });
  }

  update(datasetStudy: IDatasetStudy): Observable<EntityResponseType> {
    return this.http.put<IDatasetStudy>(this.resourceUrl, datasetStudy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetStudy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetStudy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
