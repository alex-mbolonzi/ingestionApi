import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetDataCategory, DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';
import { DatasetDataCategoryService } from './dataset-data-category.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-data-category-update',
  templateUrl: './dataset-data-category-update.component.html',
})
export class DatasetDataCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetDataCategoryId: [null, [Validators.required]],
    dataCategoryRef: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetDataCategoryService: DatasetDataCategoryService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetDataCategory }) => {
      this.updateForm(datasetDataCategory);
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

  updateForm(datasetDataCategory: IDatasetDataCategory) {
    this.editForm.patchValue({
      id: datasetDataCategory.id,
      datasetDataCategoryId: datasetDataCategory.datasetDataCategoryId,
      dataCategoryRef: datasetDataCategory.dataCategoryRef,
      datasetIdId: datasetDataCategory.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetDataCategory = this.createFromForm();
    if (datasetDataCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetDataCategoryService.update(datasetDataCategory));
    } else {
      this.subscribeToSaveResponse(this.datasetDataCategoryService.create(datasetDataCategory));
    }
  }

  private createFromForm(): IDatasetDataCategory {
    return {
      ...new DatasetDataCategory(),
      id: this.editForm.get(['id']).value,
      datasetDataCategoryId: this.editForm.get(['datasetDataCategoryId']).value,
      dataCategoryRef: this.editForm.get(['dataCategoryRef']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetDataCategory>>) {
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
