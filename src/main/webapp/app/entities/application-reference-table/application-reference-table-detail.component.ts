import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

@Component({
  selector: 'jhi-application-reference-table-detail',
  templateUrl: './application-reference-table-detail.component.html',
})
export class ApplicationReferenceTableDetailComponent implements OnInit {
  applicationReferenceTable: IApplicationReferenceTable;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationReferenceTable }) => {
      this.applicationReferenceTable = applicationReferenceTable;
    });
  }

  previousState() {
    window.history.back();
  }
}
