import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITechnicalDetails } from 'app/shared/model/technical-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { TechnicalDetailsService } from './technical-details.service';

@Component({
  selector: 'jhi-technical-details',
  templateUrl: './technical-details.component.html',
})
export class TechnicalDetailsComponent implements OnInit, OnDestroy {
  technicalDetails: ITechnicalDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected technicalDetailsService: TechnicalDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.technicalDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<ITechnicalDetails[]>) => res.ok),
        map((res: HttpResponse<ITechnicalDetails[]>) => res.body),
      )
      .subscribe(
        (res: ITechnicalDetails[]) => {
          this.technicalDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTechnicalDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITechnicalDetails) {
    return item.id;
  }

  registerChangeInTechnicalDetails() {
    this.eventSubscriber = this.eventManager.subscribe('technicalDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
