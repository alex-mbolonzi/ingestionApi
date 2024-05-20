import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetDetails } from 'app/shared/model/dataset-details.model';

@Component({
  selector: 'jhi-dataset-details-detail',
  templateUrl: './dataset-details-detail.component.html',
})
export class DatasetDetailsDetailComponent implements OnInit {
  datasetDetails: IDatasetDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetDetails }) => {
      this.datasetDetails = datasetDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
