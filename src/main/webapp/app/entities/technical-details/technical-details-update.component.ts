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
import { ITechnicalDetails, TechnicalDetails } from 'app/shared/model/technical-details.model';
import { TechnicalDetailsService } from './technical-details.service';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';

@Component({
  selector: 'jhi-technical-details-update',
  templateUrl: './technical-details-update.component.html',
})
export class TechnicalDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  ingestionrequestids: IIngestionRequestDetails[];
  targetIngestionStartDateDp: any;
  targetIngestionEndDateDp: any;

  editForm = this.fb.group({
    id: [],
    technicalRequestId: [null, [Validators.required]],
    dataLocationPath: [null, [Validators.maxLength(1024)]],
    dataRefreshFrequency: [null, [Validators.maxLength(255)]],
    targetIngestionStartDate: [],
    targetIngestionEndDate: [],
    targetPath: [null, [Validators.maxLength(1024)]],
    datasetTypeIngestionRef: [null, [Validators.maxLength(255)]],
    guestUsersEmail: [null, [Validators.maxLength(255)]],
    whitelistIpAddresses: [null, [Validators.maxLength(255)]],
    externalStagingContainerName: [null, [Validators.maxLength(255)]],
    externalDataSourceLocation: [null, [Validators.maxLength(255)]],
    gskAccessSourceLocationRef: [null, [Validators.maxLength(255)]],
    externalSourceSecretKeyName: [null, [Validators.maxLength(255)]],
    domainRequestId: [null, [Validators.maxLength(255)]],
    existingDataLocationIdentified: [null, [Validators.maxLength(1024)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    ingestionRequestIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected technicalDetailsService: TechnicalDetailsService,
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ technicalDetails }) => {
      this.updateForm(technicalDetails);
    });
    this.ingestionRequestDetailsService
      .query({ filter: 'technicaldetails-is-null' })
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

  updateForm(technicalDetails: ITechnicalDetails) {
    this.editForm.patchValue({
      id: technicalDetails.id,
      technicalRequestId: technicalDetails.technicalRequestId,
      dataLocationPath: technicalDetails.dataLocationPath,
      dataRefreshFrequency: technicalDetails.dataRefreshFrequency,
      targetIngestionStartDate: technicalDetails.targetIngestionStartDate,
      targetIngestionEndDate: technicalDetails.targetIngestionEndDate,
      targetPath: technicalDetails.targetPath,
      datasetTypeIngestionRef: technicalDetails.datasetTypeIngestionRef,
      guestUsersEmail: technicalDetails.guestUsersEmail,
      whitelistIpAddresses: technicalDetails.whitelistIpAddresses,
      externalStagingContainerName: technicalDetails.externalStagingContainerName,
      externalDataSourceLocation: technicalDetails.externalDataSourceLocation,
      gskAccessSourceLocationRef: technicalDetails.gskAccessSourceLocationRef,
      externalSourceSecretKeyName: technicalDetails.externalSourceSecretKeyName,
      domainRequestId: technicalDetails.domainRequestId,
      existingDataLocationIdentified: technicalDetails.existingDataLocationIdentified,
      createdBy: technicalDetails.createdBy,
      createdDate: technicalDetails.createdDate != null ? technicalDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: technicalDetails.modifiedBy,
      modifiedDate: technicalDetails.modifiedDate != null ? technicalDetails.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ingestionRequestIdId: technicalDetails.ingestionRequestIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const technicalDetails = this.createFromForm();
    if (technicalDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.technicalDetailsService.update(technicalDetails));
    } else {
      this.subscribeToSaveResponse(this.technicalDetailsService.create(technicalDetails));
    }
  }

  private createFromForm(): ITechnicalDetails {
    return {
      ...new TechnicalDetails(),
      id: this.editForm.get(['id']).value,
      technicalRequestId: this.editForm.get(['technicalRequestId']).value,
      dataLocationPath: this.editForm.get(['dataLocationPath']).value,
      dataRefreshFrequency: this.editForm.get(['dataRefreshFrequency']).value,
      targetIngestionStartDate: this.editForm.get(['targetIngestionStartDate']).value,
      targetIngestionEndDate: this.editForm.get(['targetIngestionEndDate']).value,
      targetPath: this.editForm.get(['targetPath']).value,
      datasetTypeIngestionRef: this.editForm.get(['datasetTypeIngestionRef']).value,
      guestUsersEmail: this.editForm.get(['guestUsersEmail']).value,
      whitelistIpAddresses: this.editForm.get(['whitelistIpAddresses']).value,
      externalStagingContainerName: this.editForm.get(['externalStagingContainerName']).value,
      externalDataSourceLocation: this.editForm.get(['externalDataSourceLocation']).value,
      gskAccessSourceLocationRef: this.editForm.get(['gskAccessSourceLocationRef']).value,
      externalSourceSecretKeyName: this.editForm.get(['externalSourceSecretKeyName']).value,
      domainRequestId: this.editForm.get(['domainRequestId']).value,
      existingDataLocationIdentified: this.editForm.get(['existingDataLocationIdentified']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      ingestionRequestIdId: this.editForm.get(['ingestionRequestIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITechnicalDetails>>) {
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
