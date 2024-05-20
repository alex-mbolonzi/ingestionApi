import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetTherapy } from 'app/shared/model/dataset-therapy.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetTherapyService } from './dataset-therapy.service';

@Component({
  selector: 'jhi-dataset-therapy',
  templateUrl: './dataset-therapy.component.html',
})
export class DatasetTherapyComponent implements OnInit, OnDestroy {
  datasetTherapies: IDatasetTherapy[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetTherapyService: DatasetTherapyService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetTherapyService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetTherapy[]>) => res.ok),
        map((res: HttpResponse<IDatasetTherapy[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetTherapy[]) => {
          this.datasetTherapies = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetTherapies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetTherapy) {
    return item.id;
  }

  registerChangeInDatasetTherapies() {
    this.eventSubscriber = this.eventManager.subscribe('datasetTherapyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
