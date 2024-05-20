import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetDetails } from 'app/shared/model/dataset-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetDetailsService } from './dataset-details.service';

@Component({
  selector: 'jhi-dataset-details',
  templateUrl: './dataset-details.component.html',
})
export class DatasetDetailsComponent implements OnInit, OnDestroy {
  datasetDetails: IDatasetDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetDetailsService: DatasetDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetDetails[]>) => res.ok),
        map((res: HttpResponse<IDatasetDetails[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetDetails[]) => {
          this.datasetDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetDetails) {
    return item.id;
  }

  registerChangeInDatasetDetails() {
    this.eventSubscriber = this.eventManager.subscribe('datasetDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
