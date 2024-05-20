import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetStudy, DatasetStudy } from 'app/shared/model/dataset-study.model';
import { DatasetStudyService } from './dataset-study.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-study-update',
  templateUrl: './dataset-study-update.component.html',
})
export class DatasetStudyUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetStudyId: [null, [Validators.required]],
    studyId: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetStudyService: DatasetStudyService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetStudy }) => {
      this.updateForm(datasetStudy);
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

  updateForm(datasetStudy: IDatasetStudy) {
    this.editForm.patchValue({
      id: datasetStudy.id,
      datasetStudyId: datasetStudy.datasetStudyId,
      studyId: datasetStudy.studyId,
      datasetIdId: datasetStudy.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetStudy = this.createFromForm();
    if (datasetStudy.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetStudyService.update(datasetStudy));
    } else {
      this.subscribeToSaveResponse(this.datasetStudyService.create(datasetStudy));
    }
  }

  private createFromForm(): IDatasetStudy {
    return {
      ...new DatasetStudy(),
      id: this.editForm.get(['id']).value,
      datasetStudyId: this.editForm.get(['datasetStudyId']).value,
      studyId: this.editForm.get(['studyId']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetStudy>>) {
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
