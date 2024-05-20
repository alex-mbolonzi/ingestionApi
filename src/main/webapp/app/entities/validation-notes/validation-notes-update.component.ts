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
import { IValidationNotes, ValidationNotes } from 'app/shared/model/validation-notes.model';
import { ValidationNotesService } from './validation-notes.service';
import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';

@Component({
  selector: 'jhi-validation-notes-update',
  templateUrl: './validation-notes-update.component.html',
})
export class ValidationNotesUpdateComponent implements OnInit {
  isSaving: boolean;

  ingestionrequestdetails: IIngestionRequestDetails[];

  editForm = this.fb.group({
    id: [],
    notesId: [null, [Validators.required]],
    notes: [null, [Validators.required, Validators.maxLength(1024)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    ingestionRequestIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected validationNotesService: ValidationNotesService,
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ validationNotes }) => {
      this.updateForm(validationNotes);
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
  }

  updateForm(validationNotes: IValidationNotes) {
    this.editForm.patchValue({
      id: validationNotes.id,
      notesId: validationNotes.notesId,
      notes: validationNotes.notes,
      createdBy: validationNotes.createdBy,
      createdDate: validationNotes.createdDate != null ? validationNotes.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: validationNotes.modifiedBy,
      modifiedDate: validationNotes.modifiedDate != null ? validationNotes.modifiedDate.format(DATE_TIME_FORMAT) : null,
      ingestionRequestIdId: validationNotes.ingestionRequestIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const validationNotes = this.createFromForm();
    if (validationNotes.id !== undefined) {
      this.subscribeToSaveResponse(this.validationNotesService.update(validationNotes));
    } else {
      this.subscribeToSaveResponse(this.validationNotesService.create(validationNotes));
    }
  }

  private createFromForm(): IValidationNotes {
    return {
      ...new ValidationNotes(),
      id: this.editForm.get(['id']).value,
      notesId: this.editForm.get(['notesId']).value,
      notes: this.editForm.get(['notes']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      ingestionRequestIdId: this.editForm.get(['ingestionRequestIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValidationNotes>>) {
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
