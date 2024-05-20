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
import { IRequestStatusDetails, RequestStatusDetails } from 'app/shared/model/request-status-details.model';
import { RequestStatusDetailsService } from './request-status-details.service';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

@Component({
  selector: 'jhi-request-status-details-update',
  templateUrl: './request-status-details-update.component.html',
})
export class RequestStatusDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  ingestionrequestdetails: IIngestionRequestDetails[];

  statuses: IStatus[];

  editForm = this.fb.group({
    id: [],
    requestStatusId: [null, [Validators.required]],
    decisionByName: [null, [Validators.required, Validators.maxLength(255)]],
    decisionByMudid: [null, [Validators.required, Validators.maxLength(255)]],
    decisionByEmail: [null, [Validators.required, Validators.maxLength(255)]],
    decisionDate: [null, [Validators.required]],
    decisionComments: [null, [Validators.maxLength(255)]],
    rejectionReason: [null, [Validators.maxLength(255)]],
    activeFlag: [null, [Validators.required]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    ingestionRequestIdId: [null, Validators.required],
    statusIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected requestStatusDetailsService: RequestStatusDetailsService,
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ requestStatusDetails }) => {
      this.updateForm(requestStatusDetails);
    });
    this.ingestionRequestDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIngestionRequestDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIngestionRequestDetails[]>) => response.body),
      )
      .subscribe(
        (res: IIngestionRequestDetails[]) => (this.ingestionrequestdetails = res),
        (res: HttpErrorResponse) => this.onError(res.message),
      );
    this.statusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body),
      )
      .subscribe(
        (res: IStatus[]) => (this.statuses = res),
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  updateForm(requestStatusDetails: IRequestStatusDetails) {
    this.editForm.patchValue({
      id: requestStatusDetails.id,
      requestStatusId: requestStatusDetails.requestStatusId,
      decisionByName: requestStatusDetails.decisionByName,
      decisionByMudid: requestStatusDetails.decisionByMudid,
      decisionByEmail: requestStatusDetails.decisionByEmail,
      decisionDate: requestStatusDetails.decisionDate != null ? requestStatusDetails.decisionDate.format(DATE_TIME_FORMAT) : null,
      decisionComments: requestStatusDetails.decisionComments,
      rejectionReason: requestStatusDetails.rejectionReason,
      activeFlag: requestStatusDetails.activeFlag,
      createdBy: requestStatusDetails.createdBy,
      createdDate: requestStatusDetails.createdDate != null ? requestStatusDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: requestStatusDetails.modifiedBy,
      modifiedDate: requestStatusDetails.modifiedDate != null ? requestStatusDetails.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ingestionRequestIdId: requestStatusDetails.ingestionRequestIdId,
      statusIdId: requestStatusDetails.statusIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const requestStatusDetails = this.createFromForm();
    if (requestStatusDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.requestStatusDetailsService.update(requestStatusDetails));
    } else {
      this.subscribeToSaveResponse(this.requestStatusDetailsService.create(requestStatusDetails));
    }
  }

  private createFromForm(): IRequestStatusDetails {
    return {
      ...new RequestStatusDetails(),
      id: this.editForm.get(['id']).value,
      requestStatusId: this.editForm.get(['requestStatusId']).value,
      decisionByName: this.editForm.get(['decisionByName']).value,
      decisionByMudid: this.editForm.get(['decisionByMudid']).value,
      decisionByEmail: this.editForm.get(['decisionByEmail']).value,
      decisionDate:
        this.editForm.get(['decisionDate']).value != null ? moment(this.editForm.get(['decisionDate']).value, DATE_TIME_FORMAT) : undefined,
      decisionComments: this.editForm.get(['decisionComments']).value,
      rejectionReason: this.editForm.get(['rejectionReason']).value,
      activeFlag: this.editForm.get(['activeFlag']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      ingestionRequestIdId: this.editForm.get(['ingestionRequestIdId']).value,
      statusIdId: this.editForm.get(['statusIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestStatusDetails>>) {
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

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }
}
