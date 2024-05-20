import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITechnicalDetails } from 'app/shared/model/technical-details.model';

@Component({
  selector: 'jhi-technical-details-detail',
  templateUrl: './technical-details-detail.component.html',
})
export class TechnicalDetailsDetailComponent implements OnInit {
  technicalDetails: ITechnicalDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ technicalDetails }) => {
      this.technicalDetails = technicalDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
