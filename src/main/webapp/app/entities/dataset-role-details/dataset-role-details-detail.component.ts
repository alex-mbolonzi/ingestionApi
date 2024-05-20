import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

@Component({
  selector: 'jhi-dataset-role-details-detail',
  templateUrl: './dataset-role-details-detail.component.html',
})
export class DatasetRoleDetailsDetailComponent implements OnInit {
  datasetRoleDetails: IDatasetRoleDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetRoleDetails }) => {
      this.datasetRoleDetails = datasetRoleDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
