import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetRoleDetailsService } from './dataset-role-details.service';

@Component({
  selector: 'jhi-dataset-role-details',
  templateUrl: './dataset-role-details.component.html',
})
export class DatasetRoleDetailsComponent implements OnInit, OnDestroy {
  datasetRoleDetails: IDatasetRoleDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetRoleDetailsService: DatasetRoleDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetRoleDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetRoleDetails[]>) => res.ok),
        map((res: HttpResponse<IDatasetRoleDetails[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetRoleDetails[]) => {
          this.datasetRoleDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetRoleDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetRoleDetails) {
    return item.id;
  }

  registerChangeInDatasetRoleDetails() {
    this.eventSubscriber = this.eventManager.subscribe('datasetRoleDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
