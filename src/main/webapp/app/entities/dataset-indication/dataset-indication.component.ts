import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDatasetIndication } from 'app/shared/model/dataset-indication.model';
import { AccountService } from 'app/core/auth/account.service';
import { DatasetIndicationService } from './dataset-indication.service';

@Component({
  selector: 'jhi-dataset-indication',
  templateUrl: './dataset-indication.component.html',
})
export class DatasetIndicationComponent implements OnInit, OnDestroy {
  datasetIndications: IDatasetIndication[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected datasetIndicationService: DatasetIndicationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.datasetIndicationService
      .query()
      .pipe(
        filter((res: HttpResponse<IDatasetIndication[]>) => res.ok),
        map((res: HttpResponse<IDatasetIndication[]>) => res.body),
      )
      .subscribe(
        (res: IDatasetIndication[]) => {
          this.datasetIndications = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDatasetIndications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDatasetIndication) {
    return item.id;
  }

  registerChangeInDatasetIndications() {
    this.eventSubscriber = this.eventManager.subscribe('datasetIndicationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
