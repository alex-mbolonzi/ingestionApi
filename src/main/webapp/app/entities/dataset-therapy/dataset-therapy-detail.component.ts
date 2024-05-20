import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';

@Component({
  selector: 'jhi-dataset-therapy-detail',
  templateUrl: './dataset-therapy-detail.component.html',
})
export class DatasetTherapyDetailComponent implements OnInit {
  datasetTherapy: IDatasetTherapy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetTherapy }) => {
      this.datasetTherapy = datasetTherapy;
    });
  }

  previousState() {
    window.history.back();
  }
}
