import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetDataCategory } from 'app/shared/model/dataset-data-category.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetDataCategoryService } from './dataset-data-category.service';

@Component({
  selector: 'jhi-dataset-data-category',
  templateUrl: './dataset-data-category.component.html',
})
export class DatasetDataCategoryComponent implements OnInit, OnDestroy {
  datasetDataCategories: IDatasetDataCategory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetDataCategoryService: DatasetDataCategoryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetDataCategoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetDataCategory[]>) => res.ok),
        map((res: HttpResponse<IDatasetDataCategory[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetDataCategory[]) => {
          this.datasetDataCategories = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetDataCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetDataCategory) {
    return item.id;
  }

  registerChangeInDatasetDataCategories() {
    this.eventSubscriber = this.eventManager.subscribe('datasetDataCategoryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
