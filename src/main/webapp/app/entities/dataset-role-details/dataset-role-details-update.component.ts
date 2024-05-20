import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetRoleDetails, DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';
import { DatasetRoleDetailsService } from './dataset-role-details.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-role-details-update',
  templateUrl: './dataset-role-details-update.component.html',
})
export class DatasetRoleDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetRoleDetailsId: [null, [Validators.required]],
    role: [null, [Validators.required, Validators.maxLength(45)]],
    email: [null, [Validators.required, Validators.maxLength(255)]],
    mudid: [null, [Validators.required, Validators.maxLength(255)]],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetRoleDetailsService: DatasetRoleDetailsService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetRoleDetails }) => {
      this.updateForm(datasetRoleDetails);
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

  updateForm(datasetRoleDetails: IDatasetRoleDetails) {
    this.editForm.patchValue({
      id: datasetRoleDetails.id,
      datasetRoleDetailsId: datasetRoleDetails.datasetRoleDetailsId,
      role: datasetRoleDetails.role,
      email: datasetRoleDetails.email,
      mudid: datasetRoleDetails.mudid,
      name: datasetRoleDetails.name,
      datasetIdId: datasetRoleDetails.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetRoleDetails = this.createFromForm();
    if (datasetRoleDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetRoleDetailsService.update(datasetRoleDetails));
    } else {
      this.subscribeToSaveResponse(this.datasetRoleDetailsService.create(datasetRoleDetails));
    }
  }

  private createFromForm(): IDatasetRoleDetails {
    return {
      ...new DatasetRoleDetails(),
      id: this.editForm.get(['id']).value,
      datasetRoleDetailsId: this.editForm.get(['datasetRoleDetailsId']).value,
      role: this.editForm.get(['role']).value,
      email: this.editForm.get(['email']).value,
      mudid: this.editForm.get(['mudid']).value,
      name: this.editForm.get(['name']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetRoleDetails>>) {
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
