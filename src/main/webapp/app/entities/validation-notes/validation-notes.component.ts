import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IValidationNotes } from 'app/shared/model/validation-notes.model';
import { AccountService } from 'app/core/auth/account.service';
import { ValidationNotesService } from './validation-notes.service';

@Component({
  selector: 'jhi-validation-notes',
  templateUrl: './validation-notes.component.html',
})
export class ValidationNotesComponent implements OnInit, OnDestroy {
  validationNotes: IValidationNotes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected validationNotesService: ValidationNotesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
  ) {}

  loadAll() {
    this.validationNotesService
      .query()
      .pipe(
        filter((res: HttpResponse<IValidationNotes[]>) => res.ok),
        map((res: HttpResponse<IValidationNotes[]>) => res.body),
      )
      .subscribe(
        (res: IValidationNotes[]) => {
          this.validationNotes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message),
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInValidationNotes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IValidationNotes) {
    return item.id;
  }

  registerChangeInValidationNotes() {
    this.eventSubscriber = this.eventManager.subscribe('validationNotesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
