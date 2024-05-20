import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

@Component({
  selector: 'jhi-dataset-tech-and-assay-detail',
  templateUrl: './dataset-tech-and-assay-detail.component.html',
})
export class DatasetTechAndAssayDetailComponent implements OnInit {
  datasetTechAndAssay: IDatasetTechAndAssay;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetTechAndAssay }) => {
      this.datasetTechAndAssay = datasetTechAndAssay;
    });
  }

  previousState() {
    window.history.back();
  }
}
