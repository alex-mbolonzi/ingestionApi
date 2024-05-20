import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

type EntityResponseType = HttpResponse<IDatasetDataCategory>;
type EntityArrayResponseType = HttpResponse<IDatasetDataCategory[]>;

@Injectable({ providedIn: 'root' })
export class DatasetDataCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-data-categories';

  constructor(protected http: HttpClient) {}

  create(datasetDataCategory: IDatasetDataCategory): Observable<EntityResponseType> {
    return this.http.post<IDatasetDataCategory>(this.resourceUrl, datasetDataCategory, { observe: 'response' });
  }

  update(datasetDataCategory: IDatasetDataCategory): Observable<EntityResponseType> {
    return this.http.put<IDatasetDataCategory>(this.resourceUrl, datasetDataCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetDataCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetDataCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
