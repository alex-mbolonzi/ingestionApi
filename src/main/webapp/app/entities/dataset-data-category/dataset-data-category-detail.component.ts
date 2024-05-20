import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

@Component({
  selector: 'jhi-dataset-data-category-detail',
  templateUrl: './dataset-data-category-detail.component.html',
})
export class DatasetDataCategoryDetailComponent implements OnInit {
  datasetDataCategory: IDatasetDataCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetDataCategory }) => {
      this.datasetDataCategory = datasetDataCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
