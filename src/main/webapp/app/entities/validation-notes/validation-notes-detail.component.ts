import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValidationNotes } from 'app/shared/model/validation-notes.model';

@Component({
  selector: 'jhi-validation-notes-detail',
  templateUrl: './validation-notes-detail.component.html',
})
export class ValidationNotesDetailComponent implements OnInit {
  validationNotes: IValidationNotes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ validationNotes }) => {
      this.validationNotes = validationNotes;
    });
  }

  previousState() {
    window.history.back();
  }
}
