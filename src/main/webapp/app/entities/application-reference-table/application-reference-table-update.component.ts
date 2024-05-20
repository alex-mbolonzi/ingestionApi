import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IApplicationReferenceTable, ApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';
import { ApplicationReferenceTableService } from './application-reference-table.service';

@Component({
  selector: 'jhi-application-reference-table-update',
  templateUrl: './application-reference-table-update.component.html',
})
export class ApplicationReferenceTableUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    referenceId: [null, [Validators.required]],
    referenceData: [null, [Validators.required, Validators.maxLength(255)]],
    referenceDataType: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    referenceOrder: [null, [Validators.required]],
    referenceGroupType: [null, [Validators.maxLength(255)]],
  });

  constructor(
    protected applicationReferenceTableService: ApplicationReferenceTableService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationReferenceTable }) => {
      this.updateForm(applicationReferenceTable);
    });
  }

  updateForm(applicationReferenceTable: IApplicationReferenceTable) {
    this.editForm.patchValue({
      id: applicationReferenceTable.id,
      referenceId: applicationReferenceTable.referenceId,
      referenceData: applicationReferenceTable.referenceData,
      referenceDataType: applicationReferenceTable.referenceDataType,
      createdBy: applicationReferenceTable.createdBy,
      createdDate: applicationReferenceTable.createdDate != null ? applicationReferenceTable.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: applicationReferenceTable.modifiedBy,
      modifiedDate: applicationReferenceTable.modifiedDate != null ? applicationReferenceTable.modifiedDate.format(DATE_TIME_FORMAT) : null,
      referenceOrder: applicationReferenceTable.referenceOrder,
      referenceGroupType: applicationReferenceTable.referenceGroupType,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationReferenceTable = this.createFromForm();
    if (applicationReferenceTable.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationReferenceTableService.update(applicationReferenceTable));
    } else {
      this.subscribeToSaveResponse(this.applicationReferenceTableService.create(applicationReferenceTable));
    }
  }

  private createFromForm(): IApplicationReferenceTable {
    return {
      ...new ApplicationReferenceTable(),
      id: this.editForm.get(['id']).value,
      referenceId: this.editForm.get(['referenceId']).value,
      referenceData: this.editForm.get(['referenceData']).value,
      referenceDataType: this.editForm.get(['referenceDataType']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      referenceOrder: this.editForm.get(['referenceOrder']).value,
      referenceGroupType: this.editForm.get(['referenceGroupType']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationReferenceTable>>) {
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
}
