import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

@Component({
  selector: 'jhi-ingestion-request-details-detail',
  templateUrl: './ingestion-request-details-detail.component.html',
})
export class IngestionRequestDetailsDetailComponent implements OnInit {
  ingestionRequestDetails: IIngestionRequestDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ingestionRequestDetails }) => {
      this.ingestionRequestDetails = ingestionRequestDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
