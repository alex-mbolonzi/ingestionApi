import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailTemplate } from 'app/shared/model/email-template.model';

@Component({
  selector: 'jhi-email-template-detail',
  templateUrl: './email-template-detail.component.html',
})
export class EmailTemplateDetailComponent implements OnInit {
  emailTemplate: IEmailTemplate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emailTemplate }) => {
      this.emailTemplate = emailTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
