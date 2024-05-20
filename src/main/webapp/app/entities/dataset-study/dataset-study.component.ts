import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetStudy } from 'app/shared/model/dataset-study.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetStudyService } from './dataset-study.service';

@Component({
  selector: 'jhi-dataset-study',
  templateUrl: './dataset-study.component.html',
})
export class DatasetStudyComponent implements OnInit, OnDestroy {
  datasetStudies: IDatasetStudy[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetStudyService: DatasetStudyService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetStudyService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetStudy[]>) => res.ok),
        map((res: HttpResponse<IDatasetStudy[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetStudy[]) => {
          this.datasetStudies = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetStudies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetStudy) {
    return item.id;
  }

  registerChangeInDatasetStudies() {
    this.eventSubscriber = this.eventManager.subscribe('datasetStudyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
