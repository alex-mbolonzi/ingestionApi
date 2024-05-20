import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';

@Component({
  selector: 'jhi-dataset-indication-detail',
  templateUrl: './dataset-indication-detail.component.html',
})
export class DatasetIndicationDetailComponent implements OnInit {
  datasetIndication: IDatasetIndication;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetIndication }) => {
      this.datasetIndication = datasetIndication;
    });
  }

  previousState() {
    window.history.back();
  }
}
