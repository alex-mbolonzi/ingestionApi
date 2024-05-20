import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetTherapy, DatasetTherapy } from 'app/shared/model/dataset-therapy.model';
import { DatasetTherapyService } from './dataset-therapy.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-therapy-update',
  templateUrl: './dataset-therapy-update.component.html',
})
export class DatasetTherapyUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetTheraphyId: [null, [Validators.required]],
    therapy: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetTherapyService: DatasetTherapyService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetTherapy }) => {
      this.updateForm(datasetTherapy);
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

  updateForm(datasetTherapy: IDatasetTherapy) {
    this.editForm.patchValue({
      id: datasetTherapy.id,
      datasetTheraphyId: datasetTherapy.datasetTheraphyId,
      therapy: datasetTherapy.therapy,
      datasetIdId: datasetTherapy.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetTherapy = this.createFromForm();
    if (datasetTherapy.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetTherapyService.update(datasetTherapy));
    } else {
      this.subscribeToSaveResponse(this.datasetTherapyService.create(datasetTherapy));
    }
  }

  private createFromForm(): IDatasetTherapy {
    return {
      ...new DatasetTherapy(),
      id: this.editForm.get(['id']).value,
      datasetTheraphyId: this.editForm.get(['datasetTheraphyId']).value,
      therapy: this.editForm.get(['therapy']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetTherapy>>) {
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
