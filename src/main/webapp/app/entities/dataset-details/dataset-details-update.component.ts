import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetDetails, DatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from './dataset-details.service';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';

@Component({
  selector: 'jhi-dataset-details-update',
  templateUrl: './dataset-details-update.component.html',
})
export class DatasetDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  ingestionrequestids: IIngestionRequestDetails[];
  analysisInitDtDp: any;
  analysisEndDtDp: any;
  dtaExpectedCompletionDateDp: any;
  retentionRulesContractDateDp: any;

  editForm = this.fb.group({
    id: [],
    datasetId: [null, [Validators.required]],
    datasetName: [null, [Validators.maxLength(255)]],
    datasetOriginSource: [null, [Validators.maxLength(255)]],
    currentDataLocationRef: [null, [Validators.maxLength(255)]],
    meteorSpaceDominoUsageFlag: [],
    ihdFlag: [],
    datasetRequiredForRef: [null, [Validators.maxLength(255)]],
    estimatedDataVolumeRef: [null, [Validators.maxLength(255)]],
    analysisInitDt: [],
    analysisEndDt: [],
    dtaContractCompleteFlag: [],
    dtaExpectedCompletionDate: [],
    datasetTypeRef: [null, [Validators.maxLength(255)]],
    informationClassificationTypeRef: [null, [Validators.maxLength(255)]],
    piiTypeRef: [null, [Validators.maxLength(255)]],
    retentionRules: [null, [Validators.maxLength(255)]],
    retentionRulesContractDate: [],
    contractPartner: [null, [Validators.maxLength(255)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    ingestionRequestIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetDetailsService: DatasetDetailsService,
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetDetails }) => {
      this.updateForm(datasetDetails);
    });
    this.ingestionRequestDetailsService
      .query({ filter: 'datasetdetails-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IIngestionRequestDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIngestionRequestDetails[]>) => response.body),
      )
      .subscribe(
        (res: IIngestionRequestDetails[]) => {
          if (!this.editForm.get('ingestionRequestIdId').value) {
            this.ingestionrequestids = res;
          } else {
            this.ingestionRequestDetailsService
              .find(this.editForm.get('ingestionRequestIdId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IIngestionRequestDetails>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IIngestionRequestDetails>) => subResponse.body),
              )
              .subscribe(
                (subRes: IIngestionRequestDetails) => (this.ingestionrequestids = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message),
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  updateForm(datasetDetails: IDatasetDetails) {
    this.editForm.patchValue({
      id: datasetDetails.id,
      datasetId: datasetDetails.datasetId,
      datasetName: datasetDetails.datasetName,
      datasetOriginSource: datasetDetails.datasetOriginSource,
      currentDataLocationRef: datasetDetails.currentDataLocationRef,
      meteorSpaceDominoUsageFlag: datasetDetails.meteorSpaceDominoUsageFlag,
      ihdFlag: datasetDetails.ihdFlag,
      datasetRequiredForRef: datasetDetails.datasetRequiredForRef,
      estimatedDataVolumeRef: datasetDetails.estimatedDataVolumeRef,
      analysisInitDt: datasetDetails.analysisInitDt,
      analysisEndDt: datasetDetails.analysisEndDt,
      dtaContractCompleteFlag: datasetDetails.dtaContractCompleteFlag,
      dtaExpectedCompletionDate: datasetDetails.dtaExpectedCompletionDate,
      datasetTypeRef: datasetDetails.datasetTypeRef,
      informationClassificationTypeRef: datasetDetails.informationClassificationTypeRef,
      piiTypeRef: datasetDetails.piiTypeRef,
      retentionRules: datasetDetails.retentionRules,
      retentionRulesContractDate: datasetDetails.retentionRulesContractDate,
      contractPartner: datasetDetails.contractPartner,
      createdBy: datasetDetails.createdBy,
      createdDate: datasetDetails.createdDate != null ? datasetDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: datasetDetails.modifiedBy,
      modifiedDate: datasetDetails.modifiedDate != null ? datasetDetails.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ingestionRequestIdId: datasetDetails.ingestionRequestIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetDetails = this.createFromForm();
    if (datasetDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetDetailsService.update(datasetDetails));
    } else {
      this.subscribeToSaveResponse(this.datasetDetailsService.create(datasetDetails));
    }
  }

  private createFromForm(): IDatasetDetails {
    return {
      ...new DatasetDetails(),
      id: this.editForm.get(['id']).value,
      datasetId: this.editForm.get(['datasetId']).value,
      datasetName: this.editForm.get(['datasetName']).value,
      datasetOriginSource: this.editForm.get(['datasetOriginSource']).value,
      currentDataLocationRef: this.editForm.get(['currentDataLocationRef']).value,
      meteorSpaceDominoUsageFlag: this.editForm.get(['meteorSpaceDominoUsageFlag']).value,
      ihdFlag: this.editForm.get(['ihdFlag']).value,
      datasetRequiredForRef: this.editForm.get(['datasetRequiredForRef']).value,
      estimatedDataVolumeRef: this.editForm.get(['estimatedDataVolumeRef']).value,
      analysisInitDt: this.editForm.get(['analysisInitDt']).value,
      analysisEndDt: this.editForm.get(['analysisEndDt']).value,
      dtaContractCompleteFlag: this.editForm.get(['dtaContractCompleteFlag']).value,
      dtaExpectedCompletionDate: this.editForm.get(['dtaExpectedCompletionDate']).value,
      datasetTypeRef: this.editForm.get(['datasetTypeRef']).value,
      informationClassificationTypeRef: this.editForm.get(['informationClassificationTypeRef']).value,
      piiTypeRef: this.editForm.get(['piiTypeRef']).value,
      retentionRules: this.editForm.get(['retentionRules']).value,
      retentionRulesContractDate: this.editForm.get(['retentionRulesContractDate']).value,
      contractPartner: this.editForm.get(['contractPartner']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      ingestionRequestIdId: this.editForm.get(['ingestionRequestIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetDetails>>) {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError(),
    );
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIngestionRequestDetailsById(index: number, item: IIngestionRequestDetails) {
    return item.id;
  }
}
