import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetStudy } from 'app/shared/model/dataset-study.model';

@Component({
  selector: 'jhi-dataset-study-detail',
  templateUrl: './dataset-study-detail.component.html',
})
export class DatasetStudyDetailComponent implements OnInit {
  datasetStudy: IDatasetStudy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetStudy }) => {
      this.datasetStudy = datasetStudy;
    });
  }

  previousState() {
    window.history.back();
  }
}
