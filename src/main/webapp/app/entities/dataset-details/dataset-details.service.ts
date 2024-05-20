import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';

type EntityResponseType = HttpResponse<IDatasetDetails>;
type EntityArrayResponseType = HttpResponse<IDatasetDetails[]>;

@Injectable({ providedIn: 'root' })
export class DatasetDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/dataset-details';

  constructor(protected http: HttpClient) {}

  create(datasetDetails: IDatasetDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datasetDetails);
    return this.http
      .post<IDatasetDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(datasetDetails: IDatasetDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(datasetDetails);
    return this.http
      .put<IDatasetDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDatasetDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDatasetDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(datasetDetails: IDatasetDetails): IDatasetDetails {
    const copy: IDatasetDetails = Object.assign({}, datasetDetails, {
      analysisInitDt:
        datasetDetails.analysisInitDt != null && datasetDetails.analysisInitDt.isValid()
          ? datasetDetails.analysisInitDt.format(DATE_FORMAT)
          : null,
      analysisEndDt:
        datasetDetails.analysisEndDt != null && datasetDetails.analysisEndDt.isValid()
          ? datasetDetails.analysisEndDt.format(DATE_FORMAT)
          : null,
      dtaExpectedCompletionDate:
        datasetDetails.dtaExpectedCompletionDate != null && datasetDetails.dtaExpectedCompletionDate.isValid()
          ? datasetDetails.dtaExpectedCompletionDate.format(DATE_FORMAT)
          : null,
      retentionRulesContractDate:
        datasetDetails.retentionRulesContractDate != null && datasetDetails.retentionRulesContractDate.isValid()
          ? datasetDetails.retentionRulesContractDate.format(DATE_FORMAT)
          : null,
      createdDate: datasetDetails.createdDate != null && datasetDetails.createdDate.isValid() ? datasetDetails.createdDate.toJSON() : null,
      modifiedDate:
        datasetDetails.modifiedDate != null && datasetDetails.modifiedDate.isValid() ? datasetDetails.modifiedDate.toJSON() : null,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.analysisInitDt = res.body.analysisInitDt != null ? moment(res.body.analysisInitDt) : null;
      res.body.analysisEndDt = res.body.analysisEndDt != null ? moment(res.body.analysisEndDt) : null;
      res.body.dtaExpectedCompletionDate = res.body.dtaExpectedCompletionDate != null ? moment(res.body.dtaExpectedCompletionDate) : null;
      res.body.retentionRulesContractDate =
        res.body.retentionRulesContractDate != null ? moment(res.body.retentionRulesContractDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((datasetDetails: IDatasetDetails) => {
        datasetDetails.analysisInitDt = datasetDetails.analysisInitDt != null ? moment(datasetDetails.analysisInitDt) : null;
        datasetDetails.analysisEndDt = datasetDetails.analysisEndDt != null ? moment(datasetDetails.analysisEndDt) : null;
        datasetDetails.dtaExpectedCompletionDate =
          datasetDetails.dtaExpectedCompletionDate != null ? moment(datasetDetails.dtaExpectedCompletionDate) : null;
        datasetDetails.retentionRulesContractDate =
          datasetDetails.retentionRulesContractDate != null ? moment(datasetDetails.retentionRulesContractDate) : null;
        datasetDetails.createdDate = datasetDetails.createdDate != null ? moment(datasetDetails.createdDate) : null;
        datasetDetails.modifiedDate = datasetDetails.modifiedDate != null ? moment(datasetDetails.modifiedDate) : null;
      });
    }
    return res;
  }
}
