import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';

@Component({
  selector: 'jhi-dataset-user-usage-restriction-detail',
  templateUrl: './dataset-user-usage-restriction-detail.component.html',
})
export class DatasetUserUsageRestrictionDetailComponent implements OnInit {
  datasetUserUsageRestriction: IDatasetUserUsageRestriction;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetUserUsageRestriction }) => {
      this.datasetUserUsageRestriction = datasetUserUsageRestriction;
    });
  }

  previousState() {
    window.history.back();
  }
}
