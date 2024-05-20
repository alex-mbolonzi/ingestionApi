import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';

@Component({
  selector: 'jhi-request-status-details-detail',
  templateUrl: './request-status-details-detail.component.html',
})
export class RequestStatusDetailsDetailComponent implements OnInit {
  requestStatusDetails: IRequestStatusDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requestStatusDetails }) => {
      this.requestStatusDetails = requestStatusDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
