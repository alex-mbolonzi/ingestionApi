import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmailTemplate } from 'app/shared/model/email-template.model';
import { AccountService } from 'app/core/auth/account.service';
import { EmailTemplateService } from './email-template.service';

@Component({
  selector: 'jhi-email-template',
  templateUrl: './email-template.component.html',
})
export class EmailTemplateComponent implements OnInit, OnDestroy {
  emailTemplates: IEmailTemplate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected emailTemplateService: EmailTemplateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.emailTemplateService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmailTemplate[]>) => res.ok),
        map((res: HttpResponse<IEmailTemplate[]>) => res.body),
      )
      .subscribe(
        (res: IEmailTemplate[]) => {
          this.emailTemplates = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmailTemplates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmailTemplate) {
    return item.id;
  }

  registerChangeInEmailTemplates() {
    this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
