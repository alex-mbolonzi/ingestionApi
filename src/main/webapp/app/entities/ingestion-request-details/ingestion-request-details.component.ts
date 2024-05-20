import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { IngestionRequestDetailsService } from './ingestion-request-details.service';

@Component({
  selector: 'jhi-ingestion-request-details',
  templateUrl: './ingestion-request-details.component.html',
})
export class IngestionRequestDetailsComponent implements OnInit, OnDestroy {
  ingestionRequestDetails: IIngestionRequestDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ingestionRequestDetailsService: IngestionRequestDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.ingestionRequestDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IIngestionRequestDetails[]>) => res.ok),
        map((res: HttpResponse<IIngestionRequestDetails[]>) => res.body),
      )
      .subscribe(
        (res: IIngestionRequestDetails[]) => {
          this.ingestionRequestDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIngestionRequestDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIngestionRequestDetails) {
    return item.id;
  }

  registerChangeInIngestionRequestDetails() {
    this.eventSubscriber = this.eventManager.subscribe('ingestionRequestDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
