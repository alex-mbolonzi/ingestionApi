import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRequestStatusDetails } from 'app/shared/model/request-status-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { RequestStatusDetailsService } from './request-status-details.service';

@Component({
  selector: 'jhi-request-status-details',
  templateUrl: './request-status-details.component.html',
})
export class RequestStatusDetailsComponent implements OnInit, OnDestroy {
  requestStatusDetails: IRequestStatusDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected requestStatusDetailsService: RequestStatusDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.requestStatusDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IRequestStatusDetails[]>) => res.ok),
        map((res: HttpResponse<IRequestStatusDetails[]>) => res.body),
      )
      .subscribe(
        (res: IRequestStatusDetails[]) => {
          this.requestStatusDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRequestStatusDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRequestStatusDetails) {
    return item.id;
  }

  registerChangeInRequestStatusDetails() {
    this.eventSubscriber = this.eventManager.subscribe('requestStatusDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
