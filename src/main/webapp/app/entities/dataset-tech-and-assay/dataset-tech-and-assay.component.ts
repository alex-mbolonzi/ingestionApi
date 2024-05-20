import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetTechAndAssayService } from './dataset-tech-and-assay.service';

@Component({
  selector: 'jhi-dataset-tech-and-assay',
  templateUrl: './dataset-tech-and-assay.component.html',
})
export class DatasetTechAndAssayComponent implements OnInit, OnDestroy {
  datasetTechAndAssays: IDatasetTechAndAssay[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetTechAndAssayService: DatasetTechAndAssayService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetTechAndAssayService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetTechAndAssay[]>) => res.ok),
        map((res: HttpResponse<IDatasetTechAndAssay[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetTechAndAssay[]) => {
          this.datasetTechAndAssays = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetTechAndAssays();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetTechAndAssay) {
    return item.id;
  }

  registerChangeInDatasetTechAndAssays() {
    this.eventSubscriber = this.eventManager.subscribe('datasetTechAndAssayListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
