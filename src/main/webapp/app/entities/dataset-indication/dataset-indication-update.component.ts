import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDatasetIndication, DatasetIndication } from 'app/shared/model/dataset-indication.model';
import { DatasetIndicationService } from './dataset-indication.service';
import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';

@Component({
  selector: 'jhi-dataset-indication-update',
  templateUrl: './dataset-indication-update.component.html',
})
export class DatasetIndicationUpdateComponent implements OnInit {
  isSaving: boolean;

  datasetdetails: IDatasetDetails[];

  editForm = this.fb.group({
    id: [],
    datasetIndicationId: [null, [Validators.required]],
    indication: [null, [Validators.required, Validators.maxLength(255)]],
    datasetIdId: [null, Validators.required],
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected datasetIndicationService: DatasetIndicationService,
    protected datasetDetailsService: DatasetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ datasetIndication }) => {
      this.updateForm(datasetIndication);
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

  updateForm(datasetIndication: IDatasetIndication) {
    this.editForm.patchValue({
      id: datasetIndication.id,
      datasetIndicationId: datasetIndication.datasetIndicationId,
      indication: datasetIndication.indication,
      datasetIdId: datasetIndication.datasetIdId,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const datasetIndication = this.createFromForm();
    if (datasetIndication.id !== undefined) {
      this.subscribeToSaveResponse(this.datasetIndicationService.update(datasetIndication));
    } else {
      this.subscribeToSaveResponse(this.datasetIndicationService.create(datasetIndication));
    }
  }

  private createFromForm(): IDatasetIndication {
    return {
      ...new DatasetIndication(),
      id: this.editForm.get(['id']).value,
      datasetIndicationId: this.editForm.get(['datasetIndicationId']).value,
      indication: this.editForm.get(['indication']).value,
      datasetIdId: this.editForm.get(['datasetIdId']).value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatasetIndication>>) {
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
