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
import { IIngestionRequestDetails, IngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from './ingestion-request-details.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';
import { ITechnicalDetails } from 'app/shared/model/technical-details.model';
import { TechnicalDetailsService } from 'app/entities/technical-details/technical-details.service';

@Component({
  selector: 'jhi-ingestion-request-details-update',
  templateUrl: './ingestion-request-details-update.component.html',
})
export class IngestionRequestDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  technicaldetails: ITechnicalDetails[];

  editForm = this.fb.group({
    id: [],
    ingestionRequestId: [null, [Validators.required]],
    requesterName: [null, [Validators.required, Validators.maxLength(255)]],
    requesterMudid: [null, [Validators.required, Validators.maxLength(255)]],
    requesterEmail: [null, [Validators.required, Validators.maxLength(255)]],
    requestRationaleReason: [null, [Validators.maxLength(255)]],
    requestedByName: [null, [Validators.required, Validators.maxLength(255)]],
    requestedByMudid: [null, [Validators.required, Validators.maxLength(255)]],
    requestedByEmail: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    modifiedReason: [null, [Validators.maxLength(255)]],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected datasetDetailsService: DatasetDetailsService,
    protected technicalDetailsService: TechnicalDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ingestionRequestDetails }) => {
      this.updateForm(ingestionRequestDetails);
    });
    this.datasetDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDatasetDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDatasetDetails[]>) => response.body),
      )
      .subscribe(
        (res: IDatasetDetails[]) => (this.datasetdetails = res),
        (res: HttpErrorResponse) => this.onError(res.message),
      );
    this.technicalDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITechnicalDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITechnicalDetails[]>) => response.body),
      )
      .subscribe(
        (res: ITechnicalDetails[]) => (this.technicaldetails = res),
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  updateForm(ingestionRequestDetails: IIngestionRequestDetails) {
    this.editForm.patchValue({
      id: ingestionRequestDetails.id,
      ingestionRequestId: ingestionRequestDetails.ingestionRequestId,
      requesterName: ingestionRequestDetails.requesterName,
      requesterMudid: ingestionRequestDetails.requesterMudid,
      requesterEmail: ingestionRequestDetails.requesterEmail,
      requestRationaleReason: ingestionRequestDetails.requestRationaleReason,
      requestedByName: ingestionRequestDetails.requestedByName,
      requestedByMudid: ingestionRequestDetails.requestedByMudid,
      requestedByEmail: ingestionRequestDetails.requestedByEmail,
      createdBy: ingestionRequestDetails.createdBy,
      createdDate: ingestionRequestDetails.createdDate != null ? ingestionRequestDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: ingestionRequestDetails.modifiedBy,
      modifiedDate: ingestionRequestDetails.modifiedDate != null ? ingestionRequestDetails.modifiedDate.format(DATE_TIME_FORMAT) : null,
      modifiedReason: ingestionRequestDetails.modifiedReason,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ingestionRequestDetails = this.createFromForm();
    if (ingestionRequestDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.ingestionRequestDetailsService.update(ingestionRequestDetails));
    } else {
      this.subscribeToSaveResponse(this.ingestionRequestDetailsService.create(ingestionRequestDetails));
    }
  }

  private createFromForm(): IIngestionRequestDetails {
    return {
      ...new IngestionRequestDetails(),
      id: this.editForm.get(['id']).value,
      ingestionRequestId: this.editForm.get(['ingestionRequestId']).value,
      requesterName: this.editForm.get(['requesterName']).value,
      requesterMudid: this.editForm.get(['requesterMudid']).value,
      requesterEmail: this.editForm.get(['requesterEmail']).value,
      requestRationaleReason: this.editForm.get(['requestRationaleReason']).value,
      requestedByName: this.editForm.get(['requestedByName']).value,
      requestedByMudid: this.editForm.get(['requestedByMudid']).value,
      requestedByEmail: this.editForm.get(['requestedByEmail']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedReason: this.editForm.get(['modifiedReason']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngestionRequestDetails>>) {
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

  trackDatasetDetailsById(index: number, item: IDatasetDetails) {
    return item.id;
  }

  trackTechnicalDetailsById(index: number, item: ITechnicalDetails) {
    return item.id;
  }
}
