import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetUserUsageRestrictionService } from './dataset-user-usage-restriction.service';

@Component({
  selector: 'jhi-dataset-user-usage-restriction',
  templateUrl: './dataset-user-usage-restriction.component.html',
})
export class DatasetUserUsageRestrictionComponent implements OnInit, OnDestroy {
  datasetUserUsageRestrictions: IDatasetUserUsageRestriction[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetUserUsageRestrictionService: DatasetUserUsageRestrictionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetUserUsageRestrictionService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetUserUsageRestriction[]>) => res.ok),
        map((res: HttpResponse<IDatasetUserUsageRestriction[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetUserUsageRestriction[]) => {
          this.datasetUserUsageRestrictions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetUserUsageRestrictions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetUserUsageRestriction) {
    return item.id;
  }

  registerChangeInDatasetUserUsageRestrictions() {
    this.eventSubscriber = this.eventManager.subscribe('datasetUserUsageRestrictionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
