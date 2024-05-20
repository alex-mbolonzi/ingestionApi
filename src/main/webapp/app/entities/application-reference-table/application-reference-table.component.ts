import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';
import { AccountService } from 'app/core/auth/account.service';
import { ApplicationReferenceTableService } from './application-reference-table.service';

@Component({
  selector: 'jhi-application-reference-table',
  templateUrl: './application-reference-table.component.html',
})
export class ApplicationReferenceTableComponent implements OnInit, OnDestroy {
  applicationReferenceTables: IApplicationReferenceTable[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected applicationReferenceTableService: ApplicationReferenceTableService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.applicationReferenceTableService
      .query()
      .pipe(
        filter((res: HttpResponse<IApplicationReferenceTable[]>) => res.ok),
        map((res: HttpResponse<IApplicationReferenceTable[]>) => res.body),
      )
      .subscribe(
        (res: IApplicationReferenceTable[]) => {
          this.applicationReferenceTables = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApplicationReferenceTables();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApplicationReferenceTable) {
    return item.id;
  }

  registerChangeInApplicationReferenceTables() {
    this.eventSubscriber = this.eventManager.subscribe('applicationReferenceTableListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
