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
import { IDatasetUserUsageRestriction, DatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';
import { DatasetUserUsageRestrictionService } from './dataset-user-usage-restriction.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-user-usage-restriction-update',
  templateUrl: './dataset-user-usage-restriction-update.component.html',
})
export class DatasetUserUsageRestrictionUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    usageUserRestrictionId: [null, [Validators.required]],
    restrictionRef: [null, [Validators.required, Validators.maxLength(255)]],
    restrictionTypeRef: [null, [Validators.required, Validators.maxLength(255)]],
    reasonForOther: [null, [Validators.maxLength(255)]],
    createdBy: [null, [Validators.required, Validators.maxLength(255)]],
    createdDate: [null, [Validators.required]],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDate: [],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetUserUsageRestrictionService: DatasetUserUsageRestrictionService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetUserUsageRestriction }) => {
      this.updateForm(datasetUserUsageRestriction);
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
  }

  updateForm(datasetUserUsageRestriction: IDatasetUserUsageRestriction) {
    this.editForm.patchValue({
      id: datasetUserUsageRestriction.id,
      usageUserRestrictionId: datasetUserUsageRestriction.usageUserRestrictionId,
      restrictionRef: datasetUserUsageRestriction.restrictionRef,
      restrictionTypeRef: datasetUserUsageRestriction.restrictionTypeRef,
      reasonForOther: datasetUserUsageRestriction.reasonForOther,
      createdBy: datasetUserUsageRestriction.createdBy,
      createdDate:
        datasetUserUsageRestriction.createdDate != null ? datasetUserUsageRestriction.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedBy: datasetUserUsageRestriction.modifiedBy,
      modifiedDate:
        datasetUserUsageRestriction.modifiedDate != null ? datasetUserUsageRestriction.modifiedDate.format(DATE_TIME_FORMAT) : null,
      datasetIdId: datasetUserUsageRestriction.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetUserUsageRestriction = this.createFromForm();
    if (datasetUserUsageRestriction.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetUserUsageRestrictionService.update(datasetUserUsageRestriction));
    } else {
      this.subscribeToSaveResponse(this.datasetUserUsageRestrictionService.create(datasetUserUsageRestriction));
    }
  }

  private createFromForm(): IDatasetUserUsageRestriction {
    return {
      ...new DatasetUserUsageRestriction(),
      id: this.editForm.get(['id']).value,
      usageUserRestrictionId: this.editForm.get(['usageUserRestrictionId']).value,
      restrictionRef: this.editForm.get(['restrictionRef']).value,
      restrictionTypeRef: this.editForm.get(['restrictionTypeRef']).value,
      reasonForOther: this.editForm.get(['reasonForOther']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDate:
        this.editForm.get(['modifiedDate']).value != null ? moment(this.editForm.get(['modifiedDate']).value, DATE_TIME_FORMAT) : undefined,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetUserUsageRestriction>>) {
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
}
