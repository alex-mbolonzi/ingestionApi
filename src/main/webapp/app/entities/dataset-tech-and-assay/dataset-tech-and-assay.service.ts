import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

type EntityResponseType = HttpResponse<IDatasetTechAndAssay>;
type EntityArrayResponseType = HttpResponse<IDatasetTechAndAssay[]>;

@Injectable({ providedIn: 'root' })
export class DatasetTechAndAssayService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-tech-and-assays';

  constructor(protected http: HttpClient) {}

  create(datasetTechAndAssay: IDatasetTechAndAssay): Observable<EntityResponseType> {
    return this.http.post<IDatasetTechAndAssay>(this.resourceUrl, datasetTechAndAssay, { observe: 'response' });
  }

  update(datasetTechAndAssay: IDatasetTechAndAssay): Observable<EntityResponseType> {
    return this.http.put<IDatasetTechAndAssay>(this.resourceUrl, datasetTechAndAssay, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDatasetTechAndAssay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDatasetTechAndAssay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
