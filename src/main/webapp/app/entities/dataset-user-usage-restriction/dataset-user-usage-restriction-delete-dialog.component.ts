import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatasetUserUsageRestriction } from 'app/shared/model/dataset-user-usage-restriction.model';
import { DatasetUserUsageRestrictionService } from './dataset-user-usage-restriction.service';

@Component({
  selector: 'jhi-dataset-user-usage-restriction-delete-dialog',
  templateUrl: './dataset-user-usage-restriction-delete-dialog.component.html',
})
export class DatasetUserUsageRestrictionDeleteDialogComponent {
  datasetUserUsageRestriction: IDatasetUserUsageRestriction;

  constructor(
    protected datasetUserUsageRestrictionService: DatasetUserUsageRestrictionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.datasetUserUsageRestrictionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'datasetUserUsageRestrictionListModification',
        content: 'Deleted an datasetUserUsageRestriction',
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dataset-user-usage-restriction-delete-popup',
  template: '',
})
export class DatasetUserUsageRestrictionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ datasetUserUsageRestriction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DatasetUserUsageRestrictionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static',
        });
        this.ngbModalRef.componentInstance.datasetUserUsageRestriction = datasetUserUsageRestriction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dataset-user-usage-restriction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dataset-user-usage-restriction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
