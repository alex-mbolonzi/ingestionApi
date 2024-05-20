import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetTechAndAssay, DatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';
import { DatasetTechAndAssayService } from './dataset-tech-and-assay.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-tech-and-assay-update',
  templateUrl: './dataset-tech-and-assay-update.component.html',
})
export class DatasetTechAndAssayUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetTechAndAssayId: [null, [Validators.required]],
    techniqueAndAssay: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetTechAndAssayService: DatasetTechAndAssayService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetTechAndAssay }) => {
      this.updateForm(datasetTechAndAssay);
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

  updateForm(datasetTechAndAssay: IDatasetTechAndAssay) {
    this.editForm.patchValue({
      id: datasetTechAndAssay.id,
      datasetTechAndAssayId: datasetTechAndAssay.datasetTechAndAssayId,
      techniqueAndAssay: datasetTechAndAssay.techniqueAndAssay,
      datasetIdId: datasetTechAndAssay.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetTechAndAssay = this.createFromForm();
    if (datasetTechAndAssay.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetTechAndAssayService.update(datasetTechAndAssay));
    } else {
      this.subscribeToSaveResponse(this.datasetTechAndAssayService.create(datasetTechAndAssay));
    }
  }

  private createFromForm(): IDatasetTechAndAssay {
    return {
      ...new DatasetTechAndAssay(),
      id: this.editForm.get(['id']).value,
      datasetTechAndAssayId: this.editForm.get(['datasetTechAndAssayId']).value,
      techniqueAndAssay: this.editForm.get(['techniqueAndAssay']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetTechAndAssay>>) {
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
